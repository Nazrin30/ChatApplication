package com.example.chatapplication.di

import com.example.chatapplication.data.datasource.MessageDatasource
import com.example.chatapplication.data.datasource.UserDatasource
import com.example.chatapplication.repository.UserRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideUserRepository(datasource: UserDatasource, messageDatasource: MessageDatasource) : UserRepository{
        return UserRepository(datasource, messageDatasource )
    }

    @Provides
    @Singleton
    fun provideUserDatasource(reference: DatabaseReference) : UserDatasource{
        return UserDatasource(reference)
    }

    @Provides
    @Singleton
    fun provideDataBaseReferances() : DatabaseReference{
        return FirebaseDatabase.getInstance().getReference("users")
    }

    @Provides
    @Singleton
    fun provideMessageDatasource(referenceUser: DatabaseReference) : MessageDatasource{
        return MessageDatasource(referenceUser)

    }

}