package com.nuutrai.reactor.util

import com.nuutrai.reactor.Reactor
import java.io.File
import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

object FileUtils {
	fun deleteFolder(file: File): Boolean {
		try {
			Files.walk(file.toPath()).use { files ->
				files.sorted(Comparator.reverseOrder<Path?>()).map<File?> { obj: Path? -> obj!!.toFile() }
					.forEach { obj: File? -> obj!!.delete() }
				return true
			}
		} catch (e: IOException) {
			Reactor.Companion.logger!!.warning(e.message)
			return false
		}
	}

	fun copyFolder(source: File, target: File, excludeFiles: MutableList<String?>?): Boolean {
		val sourceDir = source.toPath()
		val targetDir = target.toPath()

		try {
			Files.walkFileTree(sourceDir, CopyDirFileVisitor(sourceDir, targetDir, excludeFiles))
			return true
		} catch (e: IOException) {
			Reactor.Companion.logger!!.warning("Unable to copy directory " + e)
			return false
		}
	}

	private class CopyDirFileVisitor(
		private val sourceDir: Path,
		private val targetDir: Path,
		private val excludeFiles: MutableList<String?>?
	) : SimpleFileVisitor<Path?>() {
		@Throws(IOException::class)
		override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes?): FileVisitResult {
			val newDir = targetDir.resolve(sourceDir.relativize(dir))
			if (!Files.isDirectory(newDir)) {
				Files.createDirectory(newDir)
			}
			return FileVisitResult.CONTINUE
		}

		@Throws(IOException::class)
		override fun visitFile(file: Path, attrs: BasicFileAttributes?): FileVisitResult {
			// Pass files that are set to ignore
			if (excludeFiles != null && excludeFiles.contains(
					file.getFileName().toString()
				)
			) return FileVisitResult.CONTINUE
			// Copy the files
			val targetFile = targetDir.resolve(sourceDir.relativize(file))
			Files.copy(file, targetFile, StandardCopyOption.COPY_ATTRIBUTES)
			return FileVisitResult.CONTINUE
		}
	}
}
