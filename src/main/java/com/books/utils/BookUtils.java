package com.books.utils;

import com.books.model.BookDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class BookUtils {

    private static final String[] TITLES = {
            "The Silent Echo", "Whispers of Time", "Beyond the Horizon", "Shadows of Truth",
            "The Last Voyage", "Crimson Skies", "Fragments of Memory", "The Hidden Path",
            "Echoes in the Dark", "The Forgotten Realm", "Wings of Fire", "The Glass Tower",
            "Midnight's Promise", "The Wandering Star", "Silent Waters", "The Broken Chain",
            "Rivers of Gold", "The Iron Crown", "Shattered Dreams", "The Final Chapter",
            "Beneath the Stars", "The Velvet Curtain", "Ashes to Light", "The Endless Road",
            "Winter's Requiem", "The Painted Sky", "Ocean of Secrets", "The Quiet Storm",
            "Labyrinth of Souls", "The Amber Throne", "Falling Leaves", "The Distant Shore",
            "Ghosts of Yesterday", "The Sapphire Sea", "Threads of Fate", "The Marble Garden",
            "Whistling Winds", "The Copper Moon", "Undying Light", "The Frozen Lake",
            "Songs of the Deep", "The Ivory Gate", "Restless Tides", "The Golden Hour",
            "Beyond the Veil", "The Scarlet Letter's Shadow", "Twilight Reckoning", "The Obsidian Path",
            "Harbor of Dreams", "The Cracked Mirror", "Voices in the Fog", "The Emerald Crown",
            "Autumn's Requiem", "The Silver Serpent", "Lanterns at Dusk", "The Hollow Kingdom",
            "Whispering Pines", "The Jade Compass", "Storm Over the Valley", "The Weathered Map",
            "Children of the Ash", "The Quiet Rebellion", "Beneath a Pale Moon", "The Wandering Flame",
            "Echo of the Ancients", "The Salt Road", "Between Two Fires", "The Painted Veil",
            "Songbird's Lament", "The Last Cartographer", "Ruins of Elmwood", "The Gilded Cage",
            "Where Shadows Sleep", "The Cinder Path"
    };

    private static final String[] AUTHORS = {
            "Elena Marsh", "James Whitfield", "Sofia Reyes", "Marcus Chen",
            "Amara Okafor", "Liam O'Connell", "Yuki Tanaka", "Isabella Rossi",
            "David Kim", "Nadia Petrov", "Thomas Grey", "Priya Sharma",
            "Lucas Bennett", "Mei Lin", "Oscar Hendricks", "Camille Dubois",
            "Rajesh Nair", "Anna Kowalski", "Gabriel Silva", "Fatima Al-Sayed",
            "Henrik Larsson", "Chiara Bianchi", "Noah Fischer", "Aisha Ibrahim",
            "Viktor Novak", "Grace O'Malley", "Kenji Watanabe", "Ingrid Solberg",
            "Diego Fernandez", "Naomi Cohen", "Adrian Wolfe", "Zainab Hussain",
            "Peter van der Berg", "Lucia Moreno", "Samuel Okonkwo", "Freya Andersen",
            "Ravi Patel", "Clara Dupont", "Milo Jansen", "Aaliyah Johnson",
            "Stefan Novotny", "Hana Kobayashi", "Elias Berg", "Valentina Cruz",
            "Owen Fitzgerald", "Leila Farah", "Bruno Costa", "Sabina Kaur"
    };

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Map<Long, BookDTO> generateRandomBooks(int count) {
        var random = new Random();

        var result = new ConcurrentHashMap<Long, BookDTO>();

        for (var i = 0; i < count; i++) {
            var id = (long) (i + 1);
            var name = TITLES[random.nextInt(TITLES.length)];
            var author = AUTHORS[random.nextInt(AUTHORS.length)];
            var publishDate = randomDate(random).format(DATE_FORMAT);

            result.put(id, new BookDTO(id, name, author, publishDate));
        }

        return result;
    }

    private static LocalDate randomDate(Random random) {
        var year = 1980 + random.nextInt(46);
        var month = 1 + random.nextInt(12);
        var day = 1 + random.nextInt(28);

        return LocalDate.of(year, month, day);
    }
}
