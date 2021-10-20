package no.elhub.tools.autochangelog.extensions

import java.io.File
import java.io.FileInputStream

/**
 * Returns a sequence of lines from this [File] receiver
 * after (and including) the line that satisfies the [predicate] function.
 *
 * @throws IllegalArgumentException if this file is not a *normal* file
 */
fun File.linesAfter(predicate: (String) -> Boolean): Sequence<String> {
    return if (!this.isFile) throw IllegalArgumentException("File '${this.path}' is not a regular file") else sequence {
        FileInputStream(this@linesAfter).use { fis ->
            fis.bufferedReader().useLines {
                var add = false
                it.forEach { s ->
                    if (!add && predicate(s)) add = true
                    if (add) yield(s)
                }
            }
        }
    }
}