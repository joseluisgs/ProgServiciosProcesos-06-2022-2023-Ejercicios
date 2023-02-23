package com.example.exceptions

sealed class StorageException(message : String) : RuntimeException(message)

class StorageFileNotSaved(message: String) : StorageException(message)
class StorageFileNotFound(message: String) : StorageException(message)
