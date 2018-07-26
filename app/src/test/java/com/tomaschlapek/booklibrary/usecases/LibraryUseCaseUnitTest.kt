package com.tomaschlapek.booklibrary.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tomaschlapek.booklibrary.common.mock
import com.tomaschlapek.booklibrary.common.whenever
import com.tomaschlapek.booklibrary.domain.interactor.LoadLibraryUseCase
import com.tomaschlapek.booklibrary.domain.repository.ILibraryRepository
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt


class LibraryUseCaseUnitTest {

  @Rule
  @JvmField
  val rule = InstantTaskExecutorRule()

  val loadLibraryRepo = mock<ILibraryRepository>()

  val loadLibraryUseCase by lazy { LoadLibraryUseCase(loadLibraryRepo) }

  @Test
  fun testCryptoListUseCases_getCryptoList_Completed() {
    whenever(loadLibraryRepo.getItems(anyInt(), anyInt()))
      .thenReturn(Single.just(emptyList()))

    loadLibraryUseCase.execute(0, 0)
      .test()
      .assertComplete()
  }

}