package com.example.amernotsapp.ui.enums

class ConstValue {
    companion object {
        const val ROLE_USER = "ROLE_USER"
        const val ROLE_FIRE_DEPARTMENT = "ROLE_FIRE_DEPARTMENT"
        const val ROLE_AMBULANCE = "ROLE_AMBULANCE"
        const val ROLE_POLICE = "ROLE_POLICE"

        const val TOKEN_AUTH_UPDATE_INTERVAL = 60 * 60

        const val VALIDATE_LOGIN = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"

        const val STATUS_SUCCESSFULLY = "Successfully"
        const val STATUS_FAILURE = "Failure"
        const val STATUS_SOMEONE_ANSWERED = "someone already answered the news"
    }
}