package com.cypher.spacegallery

import com.cypher.spacegallery.database.GalleryDbTest
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(GalleryDbTest::class)
class InstrumentedTestSuite