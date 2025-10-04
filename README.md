# EvaMart Assignment

This project is an **Android Assignment** that demonstrates the overview of a shop using a graph-based UI with pullable inventory.  
The main focus of this assignment is to implement the **smooth pulling effect** on the "Inventory" bubble while following clean architecture principles.

---

## Features

- **Pullable Inventory**:  
  The "Inventory" bubble can be pulled to expand and display the inventory items below.  
  This pulling effect is smooth and problem-free.

- **Dummy Data Support**:  
  Products, carousel, and distributors are populated with dummy/static data from JSON and Room DB.

- **MVVM Architecture**:  
  Clean separation of concerns with View (UI), ViewModel (state management), and Repository (data handling).

- **Dependency Injection with Hilt**:  
  - `@HiltViewModel` is used in **AnalyticsViewModel** to inject the repository.  
  - `DatabaseModule` provides `AppDatabase`, `InventoryDao`, and `Gson` using `@Module` and `@Provides`.  
  - Application class `EvaMartApp` is annotated with `@HiltAndroidApp` to enable Hilt.

- **Room Database**:  
  Room is used to store and retrieve inventory items (utilized, soon_stock, revision_needed).

- **LiveData + Flow**:  
  - Room DAO returns `Flow`, which is observed in ViewModels as `LiveData` using `asLiveData()`.  
  - Finance data is exposed as `LiveData` directly from JSON.

---

## Tech Stack

- **Language**: Kotlin  
- **Architecture**: MVVM (Model - ViewModel - View)  
- **Dependency Injection**: Hilt  
- **Database**: Room  
- **Coroutines + Flow**: For async tasks and reactive data streams  
- **ViewBinding**: For easy UI binding  
- **RecyclerView**: For displaying inventory lists  
- **Gson**: For parsing JSON data  

---

## Project Structure

- **`AnalyticsRepository`**  
  Handles fetching data from local JSON and inserting inventory into Room DB.

- **`AnalyticsViewModel`**  
  - Annotated with `@HiltViewModel`  
  - Repository is injected using Hilt  
  - Exposes LiveData for finance and inventory categories

- **`DatabaseModule`**  
  - Annotated with `@Module` and `@InstallIn(SingletonComponent::class)`  
  - Provides `AppDatabase`, `InventoryDao`, and `Gson` with `@Provides`

- **`EvaMartApp`**  
  - Annotated with `@HiltAndroidApp`  
  - Base Application class to enable Hilt dependency injection.

---

## Setup Instructions

1. Clone the repo:
   ```bash
   git clone https://github.com/your-username/EvaMartAssignment.git
   cd EvaMartAssignment
