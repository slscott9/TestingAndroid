package com.example.testing.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.testing.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/*
    Junit is used to test any code that runs on the JVM
    @RunWith(AndroidJUnit::class) specifies that tests in this class will run on an android device(instrumented tests that test android components)

    @LargeTests-uitests
    @MediumTest-integration tests-how android components of the app work together(ex viewmodel and a fragment)
    @SmallTest-tells junit these tests are unit tests-tests single unit of our app

*/
@ExperimentalCoroutinesApi //for run blocking function
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest{

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

/*
    @Before-allows us to create instances that will be different for each test.We dont want global variables with tests

    Room.inMemoryDatabaseBuilder()-only be saved in memory for this test case we dont want new databases created for eel every time we test
    .allowMainThreadQueries()-Test cases we want queries called from the main thread(singlethread) If this were not the case we could have different thread manipulating things
    and our tests would not be consistent(flaky)

    @After-close the database after every test

*/

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.shoppingDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

/*
Test-suspend fun insertShoppingItem(shoppingItem:ShoppingItem)

Note it is suspend so it has to be run from a coroutine but we dont want concurrency in test cases so we user unblocking

runBlocking-way to executecoroutine  fromm a in thread.Bloc kmain thread until finished

Use run BlockingTest function to skip any delay

*/

    @Test
    fun insertShoppingItem() = runBlockingTest{
        val shoppingItem = ShoppingItem("name",1,1f,"url",id=1)
        dao.insertShoppingItem(shoppingItem)

        //returns live data which returns asynchounously we dont want that in testing so we use getorwaitvalue function that wait to return live data with 2 second time out

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)

        //This test will fail because
    }
}
