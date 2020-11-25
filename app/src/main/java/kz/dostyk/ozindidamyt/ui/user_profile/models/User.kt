package kz.dostyk.ozindidamyt.ui.user_profile.models

data class User(
        var info: String = "",
        var photo: String = "",
        var phoneNumber: String = "",
        var imgStorageName: String = "",
        var email: String = "",
        var dostykName: String = "",
        var currentBook: String = "",
        var userType: String = "",
        var firebaseStorageImageName: String = "",
        var enterDate: String = "",
        var userRating: Int = 0,
        var point: Int = 0,
        var bookCount: Int = 0) {
}