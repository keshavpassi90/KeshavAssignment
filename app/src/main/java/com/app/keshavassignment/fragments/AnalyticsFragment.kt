package com.app.keshavassignment.fragments

import android.os.Bundle
import android.view.LayoutInflater // REQUIRED
import android.view.View
import android.view.ViewGroup // REQUIRED
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.keshavassignment.R
import com.app.keshavassignment.adapters.FinanceAdapter
import com.app.keshavassignment.adapters.StockListAdapter
import com.app.keshavassignment.databinding.FragmentAnalyticsBinding
import com.app.keshavassignment.model.InventoryItem
import com.app.keshavassignment.view_models.AnalyticsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalyticsFragment : Fragment() {
    // 1. Use a backing property for reliable non-null access
    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnalyticsViewModel by viewModels()

    // Arrays will be initialized via LiveData observation
    private lateinit var utilizedArray : List<InventoryItem>
    private lateinit var soonStockArray : List<InventoryItem>
    private lateinit var revisionArray : List<InventoryItem>
    private var selectedCircleId: Int? = null

    // ⭐ STEP 1: Implement onCreateView to inflate the layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root // Return the root view of the layout
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // No need for FragmentAnalyticsBinding.bind(view) anymore!

        showDataViewpager()

        observeFinance()

        // ⭐ STEP 2: Fix view access hierarchy in setUpClicks()
        setUpClicks()
    }


    fun setUpClicks(){
        // Access views through the inventoryCard field on the binding object
        val utilizedCircle = binding.circle1Utilized
        val soonOutCircle = binding.circle2SoonOut
        val revisionsCircle = binding.circle3Revisions

        // Use View.OnClickListener to handle all three clicks with one function
        val clickListener = View.OnClickListener { v ->
            v?.let { handleCircleClick(it.id) }
        }

        utilizedCircle.setOnClickListener(clickListener)
        soonOutCircle.setOnClickListener(clickListener)
        revisionsCircle.setOnClickListener(clickListener)
    }

    private fun handleCircleClick(clickedId: Int) {
        // Correctly access views via the hierarchy:
        val stockCL = binding.stockCL
        val nestedScrollView = binding.nestedScrollView

        // ⭐ FIX 1: Access the CardView container for a better starting point
        val inventryCardContainer = binding.inventryCard

        // ⭐ FIX 2: Get the toolbar view reference
        val toolbarView = binding.customToolbarLayout.toolBarCL

        // ⭐ FIX 3: Get the height using the standard Kotlin property access
        val toolbarHeight = toolbarView.height // Use .height directly on the View reference

        // 1. Determine if we need to hide or toggle
        if (selectedCircleId == clickedId && stockCL.visibility == View.VISIBLE) {
            stockCL.visibility = View.GONE
            selectedCircleId = null
        } else {
            // 2. Set the data source and show the list
            updateInventoryList(clickedId)
            stockCL.visibility = View.VISIBLE
            selectedCircleId = clickedId

            // 3. Smooth Scroll to the list container (stockCL) with offset
            stockCL.post {

                // Calculate the position of stockCL relative to the inner ConstraintLayout:
                // stockCL.top (offset from inventryCard) + inventryCardContainer.top (offset from ConstraintLayout)
                val absoluteTargetY = inventryCardContainer.top + stockCL.top

                // Adjust the scroll position by subtracting the toolbar height
                val scrollTargetY = absoluteTargetY - toolbarHeight

                nestedScrollView.smoothScrollTo(0, scrollTargetY)
            }
        }
    }


    private fun updateInventoryList(clickedId: Int) {
        // ⭐ Access nested views through inventoryCard and stockCL
        val stockTxtTV = binding.stockTxtTV

        when (clickedId) {
            binding.circle1Utilized.id -> {
                stockTxtTV.text = getString(R.string.utilized)+" "+utilizedArray.size
                setUpUtilizedAdapter()
            }
            binding.circle2SoonOut.id -> {
                stockTxtTV.text = getString(R.string.soon_out_of_stock)+" "+soonStockArray.size
                setUpSoonStockAdapter()
            }
            binding.circle3Revisions.id -> {
                stockTxtTV.text = getString(R.string.revision_needed)+" "+revisionArray.size
                setUpRevisionAdapter()
            }
            else -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // ⭐ Crucial: Clear the binding reference to avoid memory leaks
        _binding = null
    }

    private fun showDataViewpager() {
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.clipToPadding = false
        binding.viewPager.clipChildren = false
    }

    private fun observeFinance() {
        viewModel.financeData.observe(viewLifecycleOwner) { financeList ->
            val adapter = FinanceAdapter(financeList)
            binding.viewPager.adapter = adapter
            binding.viewPager.setPageTransformer { page: View, position: Float ->
                val r = 1 - kotlin.math.abs(position)
                page.scaleY = 0.75f + r * 0.15f
                page.alpha = 1.0f + r * 1.0f
                page.scaleX=0.90f + r * 0.11f
            }
        }

        viewModel.utilizedInventory.observe(viewLifecycleOwner) { utilizedList ->
            utilizedArray = utilizedList
            binding.tvUtilizedCount.text = utilizedList.size.toString()
        }

        viewModel.soonStockInventory.observe(viewLifecycleOwner) { soonStockList ->
            soonStockArray = soonStockList
            binding.tvSoonOutCount.text = soonStockList.size.toString()
        }

        viewModel.revisionNeededInventory.observe(viewLifecycleOwner) { revisionList ->
            revisionArray = revisionList
            binding.tvRevisionsCount.text = revisionList.size.toString()
        }
    }

    // Adapter Setup Functions (No changes needed here, assuming StockListAdapter is defined)

    fun setUpSoonStockAdapter(){
        binding.listData.layoutManager = LinearLayoutManager(requireActivity())
        binding.listData.isNestedScrollingEnabled = false
        binding.listData.adapter = StockListAdapter(soonStockArray)
    }

    fun setUpUtilizedAdapter(){
        binding.listData.layoutManager = LinearLayoutManager(requireActivity())
        binding.listData.isNestedScrollingEnabled = false
        binding.listData.adapter = StockListAdapter(utilizedArray)
    }

    fun setUpRevisionAdapter(){
        binding.listData.layoutManager = LinearLayoutManager(requireActivity())
        binding.listData.isNestedScrollingEnabled = false
        binding.listData.adapter = StockListAdapter(revisionArray)
    }
}
