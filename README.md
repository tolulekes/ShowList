# Country List

A simple android application that allows users see country details like the region, Capital and code


https://github.com/user-attachments/assets/897fe738-32c5-401a-bbb5-8d403ad959b4

## Features

- List showing the country name, code, capital and region


## Requirements

- Android SDK 24+
- Android Studio Meerkat
- kotlin 2.1.20


## Installation
1. Clone the repository
2. Open `ShowList` folder in Android Studio
3. Select the run configuretion, options are:
   - composesolution (Jetpack Compose)
   - app (XML)
4. Build and Run the project


## Architecture

The project follows the MVVM (Model-View-ViewModel) architecture patter and uses xml and jetpack Compose for the user interface as I was not sure if should just build in xml or compose. There was also a separation of concerrns as the project is  modular. the `commmon` module has the model of the application, the `app` and `composesolution` modules get internet access throught the `common` module.

- **Models**: Data source for the app is in the `common` module along with repositories and the dependency injection
- **Views**: Jetpack Compose and Xml were both utilized
- **ViewModel**: Data is presented to the view here, the viewModel also manages the ViewState
