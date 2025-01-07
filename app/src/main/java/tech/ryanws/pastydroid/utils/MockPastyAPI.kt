package tech.ryanws.pastydroid.utils

object MockPastyAPI {
    private val fakePastes = mutableListOf<Paste>()
    
    init {
        // Generate some fake data
        for (i in 1..100) {
            fakePastes.add(
                Paste(
                    id = i,
                    content = "This is a sample paste #$i with some content. " +
                            "It might contain multiple lines or various text content. " +
                            "This is just for testing purposes."
                )
            )
        }
    }

    fun listPastes(page: Int = 1, perPage: Int = 20): List<Paste> {
        val startIndex = (page - 1) * perPage
        val endIndex = minOf(startIndex + perPage, fakePastes.size)
        return if (startIndex < fakePastes.size) {
            fakePastes.subList(startIndex, endIndex)
        } else {
            emptyList()
        }
    }

    fun getPaste(id: Int): Paste? {
        return fakePastes.find { it.id == id }
    }

    fun createPaste(content: String): Boolean {
        val newId = (fakePastes.maxOfOrNull { it.id } ?: 0) + 1
        return fakePastes.add(Paste(newId, content))
    }
} 