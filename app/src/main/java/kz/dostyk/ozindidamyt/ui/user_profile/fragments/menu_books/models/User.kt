package kz.dostyk.ozindidamyt.ui.user_profile.fragments.menu_books.models

data class User(
        var info: String = "",
        var photo: String = "",
        var phoneNumber: String = "",
        var imgStorageName: String = "",
        var email: String = "",
        var userType: String = "",
        var rating: Int = 0,
        var point: Int = 0,
        var bookCount: Int = 0) {
}