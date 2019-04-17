package phuchh.sunasterisk.projectmoviedb.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Actor(id: String?, name: String?, profilePath: String?) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Actor> {
        override fun createFromParcel(parcel: Parcel): Actor {
            return Actor(parcel)
        }

        override fun newArray(size: Int): Array<Actor?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    @SerializedName("id")
    @Expose
    private var mId: String? = id

    @SerializedName("name")
    @Expose
    private var mName: String? = name

    @SerializedName("profile_path")
    @Expose
    private var mProfilePath: String? = profilePath

    @SerializedName("character")
    @Expose
    private var mCharacter: String? = null

    @SerializedName("birthday")
    @Expose
    private var mBirthday: String? = null

    @SerializedName("biography")
    @Expose
    private var mBiography: String? = null

    @SerializedName("place_of_birth")
    @Expose
    private var mPlace: String? = null

    @SerializedName("popularity")
    @Expose
    private var mPopularity: String? = null

    @SerializedName("gender")
    @Expose
    private var mGender: String? = null

    @SerializedName("known_for_department")
    @Expose
    private var mDepartment: String? = null

    fun getId(): String? {
        return mId
    }

    fun setId(id: String) {
        mId = id
    }

    fun getName(): String? {
        return mName
    }

    fun setName(name: String) {
        mName = name
    }

    fun getProfilePath(): String? {
        return mProfilePath
    }

    fun setProfilePath(profilePath: String) {
        mProfilePath = profilePath
    }

    fun getCharacter(): String? {
        return mCharacter
    }

    fun setCharacter(character: String) {
        mCharacter = character
    }

    fun getBirthday(): String? {
        return mBirthday
    }

    fun setBirthday(birthday: String) {
        mBirthday = birthday
    }

    fun getBiography(): String? {
        return mBiography
    }

    fun setBiography(biography: String) {
        mBiography = biography
    }

    fun getPlace(): String? {
        return mPlace
    }

    fun setPlace(place: String) {
        mPlace = place
    }

    fun getPopularity(): String? {
        return mPopularity
    }

    fun setPopularity(popularity: String) {
        mPopularity = popularity
    }

    fun getGender(): String? {
        return mGender
    }

    fun setGender(gender: String) {
        mGender = gender
    }

    fun getDepartment(): String? {
        return mDepartment
    }

    fun setDepartment(department: String) {
        mDepartment = department
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(mId)
        dest.writeString(mName)
        dest.writeString(mProfilePath)
    }
}
