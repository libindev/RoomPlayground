package com.example.roomplayground.repository

import com.example.roomplayground.database.dao.UserInfoDao
import com.example.roomplayground.model.UserInfo
import com.example.roomplayground.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class UserRepository @Inject constructor(private val userInfo: UserInfoDao) {

    fun saveUserData(name: String, email: String, phoneNo: String): Flow<State<Boolean>> {
        return flow {
            try {
                userInfo.insert(
                    UserInfo(
                        id = null,
                        name = name,
                        email = email,
                        phone = phoneNo,
                        createdDate = Calendar.getInstance().time
                    )
                )
                return@flow emit(State.Success(true))
            } catch (e: Exception) {
                println(e.stackTrace)
                return@flow emit(State.Failed(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getUserDetails(): Flow<State<List<UserInfo>>> {
        return flow {
            try {
                val userData = userInfo.getUserData()!!
                return@flow emit(State.Success(userData))
            } catch (e: Exception) {
                emit(State.Failed(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}
