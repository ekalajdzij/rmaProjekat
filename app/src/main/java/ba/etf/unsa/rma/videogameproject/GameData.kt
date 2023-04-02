package ba.etf.unsa.rma.videogameproject

class GameData {
    companion object VideoGames {
        fun getAll() :List<Game> {
            return listOf(
                Game(
                    "FIFA 23",
                    "PlayStation/PC/Nintendo Switch/Xbox",
                    "27.09.2022",
                    2.0,
                    "",
                    "E3+",
                    "EA Sports",
                    "EA",
                    "Sports",
                    "FIFA 23 features both men's and women's World Cup game modes, replicating the 2022 FIFA World Cup and the 2023 FIFA Women's World Cup.",
                    mutableListOf(UserReview("alexisManFromColombia", 4, "A game that is only satisfying when playing kick-off mode with friends or playing ProClubs, career mode gets worse every year, it's really getting painful to play that mode-"),
                        UserReview("megaJohnFromCincinatti", 1617373200000, "Pro Clubs is a hot mess once again. Your player will feel completely different from one match to the next, unlike any other previous year. The RB+LB “power shot” might be the most useless addition to the game since Volta."),
                        UserRating("xboxIsBetterThanPS", 1640995200000, 2.5),
                        UserReview("beBraveForaLifetime",1704182399000,"It’s fun for the first 20-30 hours or on a lower difficulty but once you kick the difficulty up you get to experience how terrible this game really is. Defending is a joke, the way the ball will magically bounce to the ai after a tackle, the wonder tackles that the ai back line put in and the ai blocking is absolutely insane."),
                        UserReview("PESnerd", 1684301400000, "Every year we see some amazing cutscenes, but it all stays at that. I need to see a lot of improvements next year."),
                        UserRating("maloGusto", 1640995200000, 3.3),
                        UserReview("gamer4life", 1647448800000, "Average game at best. Focus is shifted across from career mode to ultimate team and adding women's teams. World cup mode was nice, but should have had a better ultimate team mode."),
                        UserReview("johnTheGreatest345", 1640995200000, "Just as usual, garbage. Every single year you guys managed to make the game worse, and worse, and worse, and worse, and worse. None of the logistics of the game makes sense. I can play 1 game against an all prime icon team and win like it was the little league team then the next game I play an all silver team and get my arse absolutely wiped. No sense whatsoever. How do I manage to get TOTY Courtois for him to play worse than a bronze goalkeeper?")
                    )),
                Game(
                    "Minecraft", "PlayStation/PC/Nintendo Switch/Xbox", "18.11.2011", 4.9,
                    "", "E10+", "Mojang Studios", "Mojang Studios", "Sandbox",
                    "In Minecraft, players explore a blocky, procedurally generated, three-dimensional world with virtually infinite terrain and may discover and extract raw materials, craft tools and items, and build structures, earthworks, and machines.",
                    mutableListOf(UserReview("akidWhoLovesGames", 1704182399000, "Kids can learn creative thinking, geometry, and even a little geology as they build imaginative block structures in this refreshingly open-ended mining and construction game. Given carte blanche to sculpt virtually any creation of their choice in this 3-D space, kids can try out tons of possibilities while working toward simple objectives. "),
                        UserRating("WindyCityyyyyyy", 1654096200000, 5.0),
                        UserRating("sotox",1660684800000,4.5),
                        UserReview("aConcernedFather", 1526402400000, "Minecraft is an excellent game for young children to help encourage their creativity and problem solving. It’s also an excellent way to bond with kids, I play it all the time with my son. One thing parents need to be aware of though is the online features and micro transactions. "),
                        UserRating("zoxxx921", 1654096200000, 5.0),
                        UserReview("jimJimsonJimmmmmyYeah", 1654096200000, "I love creating in Minecraft, but my gratification is due, at least partially, to the fact that I have to earn everything. Survival Mode generates a random world with nothing to your name, forced to gather resources in order to construct food, shelter and tools. By the time I crafted and installed a wooden door in my first mud hut I felt a sense of ownership.Minecraft, more than any other game I know, isn’t about playing it a specific way; it’s an open-world, a blank page just daring you to jump in and do with it what you will. The question, then, isn’t what you need to do to succeed, what’s needed to win, but what are you willing to do to make your dreams come to life?"),
                        UserRating("sofieFromTheChicago", 1514764800000, 4.1))
                ),
                Game(
                    "World of Warcraft",
                    "PC",
                    "23.11.2004",
                    3.8,
                    "",
                    "T10+",
                    "Blizzard Entertainment",
                    "Blizzard Entertainment",
                    "Action",
                    "Set in the fictional world of Azeroth, WoW allows players to create avatar-style characters and explore a sprawling universe while interacting with nonreal players—called nonplayer characters (NPCs)—and other real-world players (PCs)",
                    mutableListOf()
                ),
                Game(
                    "Fortnite",
                    "PlayStation/PC/Nintendo Switch/Xbox",
                    "21.07.2017",
                    3.8,
                    "",
                    "T13+",
                    "Epic Games",
                    "Epic Games",
                    "Battle royale",
                    "In Fortnite, players collaborate to survive in an open-world environment, by battling other characters who are controlled either by the game itself, or by other players.",
                    mutableListOf()
                ),
                Game(
                    "F1 22",
                    "PlayStation/PC/Xbox",
                    "28.06.2022",
                    2.4,
                    "",
                    "E8+",
                    "Codemasters",
                    "EA Sports",
                    "Racing",
                    "F1 22 is a racing video game that simulates the official Formula 1 championship. It is the official game of the Formula 1",
                    mutableListOf()
                ),
                Game(
                    "GTA V",
                    "PlayStation/PC/Xbox",
                    "17.09.2013",
                    4.5,
                    "",
                    "T18+",
                    "Rockstar Games",
                    "Rockstar Games",
                    "Third-person action game",
                    "Grand Theft Auto V is an action-adventure game played from either a third-person or first-person perspective. Players complete missions—linear scenarios with set objectives—to progress through the story. Outside of the missions, players may freely roam the open world.",
                    mutableListOf()
                ),
                Game(
                    "COD Cold War",
                    "PlayStation/PC/Xbox",
                    "13.11.2020",
                    3.0,
                    "",
                    "M18+",
                    "Activison",
                    "Activision",
                    "Action",
                    "Call of Duty: Black Ops Cold War is set during the Cold War in the early 1980s. The campaign follows Green Beret turned CIA SAD/SOG officer Russell Adler (Bruce Thomas) and his mission to stop an international espionage threat named Perseus (William Salyers) in 1981.",
                    mutableListOf()
                ),
                Game(
                    "Hitman 3",
                    "PlayStation/PC/Xbox",
                    "20.01.2021",
                    4.4,
                    "",
                    "M17+",
                    "IO Interactive",
                    "IO Interactive",
                    "Action",
                    "Hitman 3 is a stealth game played from a third-person perspective and players once again assume control of assassin Agent 47.",
                    mutableListOf()
                ),
                Game(
                    "League of Legends",
                    "PC",
                    "27.10.2009",
                    3.0,
                    "",
                    "T13+",
                    "Riot Games",
                    "Riot Games",
                    "Multiplayer online battle arena",
                    "League of Legends is one of the world's most popular video games, developed by Riot Games. It features a team-based competitive game mode based on strategy and outplaying opponents. Players work with their team to break the enemy Nexus before the enemy team breaks theirs.",
                    mutableListOf()
                ),
                Game(
                    "NBA 2K23",
                    "PlayStation/PC/Xbox",
                    "08.09.2022",
                    2.5,
                    "",
                    "E4+",
                    "Visual Concepts",
                    "2K Sports",
                    "Sport",
                    "NBA 2K23 is a 2022 basketball based on the National Basketball Association (NBA).",
                    mutableListOf()
                )

            )
        }

        fun getDetails(title: String) : Game? {
            val games = getAll()
            for (i in 0..9) {
                if(games[i].title == title) return games[i]
            }
            return null
        }
    }
}