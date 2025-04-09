package com.example.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startUpCompilationModeNone() = startUp(CompilationMode.None())

    @Test
    fun startUpCompilationModePartial() = startUp(CompilationMode.Partial())

    @Test
    fun scrollCompilationModeNone() = scrollAndNavigate(CompilationMode.None())

    @Test
    fun scrollCompilationModePartial() = scrollAndNavigate(CompilationMode.Partial())

    fun startUp(mode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.example.easyquran",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = mode
    ) {
        pressHome()
        startActivityAndWait()
    }

    fun scrollAndNavigate(mode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.example.easyquran",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = mode
    ) {
        pressHome()
        startActivityAndWait()

        scrollDownAndUp()
    }
}

fun MacrobenchmarkScope.scrollDownAndUp() {
    val list = device.findObject(By.res("chapter_list"))

    device.waitForIdle()
    list.setGestureMargin(device.displayWidth / 5)
    list.fling(Direction.DOWN)
    list.fling(Direction.UP)
    list.fling(Direction.UP)

    device.findObject(By.text("Al-Fatihah")).click()
    device.wait(Until.hasObject(By.res("verse_list")), 5000)
}