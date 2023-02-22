package es.ruymi.exceptions

sealed class StorageException(message: String) : RuntimeException(message)
class StorageFileNotFoundException(message: String) : StorageException(message)
class StorageFileNotSaveException(message: String) : StorageException(message)


