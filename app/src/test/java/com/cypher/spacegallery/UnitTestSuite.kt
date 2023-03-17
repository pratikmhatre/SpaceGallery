package com.cypher.spacegallery

import com.cypher.spacegallery.gallery_details.presentation.GalleryDetailsViewModelTest
import com.cypher.spacegallery.gallery_list.presentation.GalleryListViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(GalleryListViewModelTest::class, GalleryDetailsViewModelTest::class)
class UnitTestSuite