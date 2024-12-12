import firebase_admin
from firebase_admin import credentials, firestore
cred = credentials.Certificate('./firebase-admin-services.json')
firebase_admin.initialize_app(cred)
db = firestore.client()

def guardar_cuestionario(cuestionario):
    collection_name = 'Quizzes'
    doc_ref = db.collection(collection_name).document(cuestionario['name'])
    doc_ref.set(cuestionario)
    print(f"Cuestionario {cuestionario['name']} guardado exitosamente")
cuestionarioMChoice = {
    "name": "Wintendo",
    "language": "ES",
    "questions": [
            {
                "questionTitle": "Selecciona los personajes que aparecen en Super Smash Bros. Ultimate:",
                "questionType": "MultipleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Mario"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Link"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Master Chief"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Pikachu"
                    }
                ]
            },
            {
                "questionTitle": "Selecciona los juegos desarrollados por Nintendo:",
                "questionType": "MultipleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "The Legend of Zelda: Breath of the Wild"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Halo Infinite"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Animal Crossing: New Horizons"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "God of War"
                    }
                ]
            },
            {
                "questionTitle": "Selecciona las consolas lanzadas por Nintendo:",
                "questionType": "MultipleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Nintendo Switch"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "PlayStation 5"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Nintendo 3DS"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Xbox Series X"
                    }
                ]
            },
            {
                "questionTitle": "Selecciona los personajes de la serie The Legend of Zelda:",
                "questionType": "MultipleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Link"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Zelda"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Samus"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Ganondorf"
                    }
                ]
            },
            {
                "questionTitle": "Selecciona los juegos que pertenecen a la serie Mario Kart:",
                "questionType": "MultipleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Mario Kart 8 Deluxe"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Mario Kart Tour"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Gran Turismo 7"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Mario Kart DS"
                    }
                ]
            },
            {
                "questionTitle": "Selecciona los juegos que incluyen a Pikachu como personaje jugable:",
                "questionType": "MultipleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Pokémon Sword and Shield"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Super Smash Bros. Ultimate"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Digimon Story: Cyber Sleuth"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Pokémon Let's Go, Pikachu!"
                    }
                ]
            },
            {
                "questionTitle": "Selecciona los juegos que son exclusivos de Nintendo Switch:",
                "questionType": "MultipleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Splatoon 3"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "The Legend of Zelda: Breath of the Wild"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Horizon Zero Dawn"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Super Mario Odyssey"
                    }
                ]
            },
            {
                "questionTitle": "Selecciona los personajes que aparecen en Animal Crossing: New Horizons:",
                "questionType": "MultipleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Tom Nook"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Isabelle"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Kratos"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Blathers"
                    }
                ]
            },
            {
                "questionTitle": "Selecciona los juegos que pertenecen a la serie Metroid:",
                "questionType": "MultipleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Metroid Dread"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Metroid Prime"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Halo 3"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Metroid Fusion"
                    }
                ]
            },
            {
                "questionTitle": "Selecciona los juegos que incluyen a Kirby como personaje principal:",
                "questionType": "MultipleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Kirby Star Allies"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Kirby's Dream Land"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Sonic the Hedgehog"
                    },
                    {
                        "isAnswer": True,
                        "answerText": "Kirby and the Forgotten Land"
                    }
                ]
            },
            {
                "questionTitle": "¿Quién es el creador de Super Mario?",
                "questionType": "SingleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Shigeru Miyamoto"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Hideo Kojima"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Satoru Iwata"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Masahiro Sakurai"
                    }
                ]
            },
            {
                "questionTitle": "¿Cuál es la consola más reciente de Nintendo?",
                "questionType": "SingleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Nintendo Switch"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Nintendo Wii U"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Nintendo 3DS"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Nintendo DS"
                    }
                ]
            },
            {
                "questionTitle": "¿Cuál es el nombre del villano principal en la serie The Legend of Zelda?",
                "questionType": "SingleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Ganondorf"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Bowser"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Ridley"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "King Dedede"
                    }
                ]
            },
            {
                "questionTitle": "¿Cuál es el juego más vendido de Nintendo Switch?",
                "questionType": "SingleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Mario Kart 8 Deluxe"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "The Legend of Zelda: Breath of the Wild"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Animal Crossing: New Horizons"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Super Smash Bros. Ultimate"
                    }
                ]
            },
            {
                "questionTitle": "¿Cuál es el nombre del protagonista en la serie Metroid?",
                "questionType": "SingleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Samus Aran"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Link"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Mario"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Donkey Kong"
                    }
                ]
            },
            {
                "questionTitle": "¿Cuál es el nombre del juego de construcción y gestión de islas de Nintendo?",
                "questionType": "SingleChoice",
                "answers": [
                    {
                        "isAnswer": True,
                        "answerText": "Animal Crossing: New Horizons"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Stardew Valley"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "Harvest Moon"
                    },
                    {
                        "isAnswer": False,
                        "answerText": "The Sims"
                    }
                ]
            },
            {
                    "questionTitle": "¿Cuál es el nombre del juego de disparos en tercera persona exclusivo de Nintendo?",
                    "questionType": "SingleChoice",
                    "answers": [
                        {
                            "isAnswer": True,
                            "answerText": "Splatoon 3"
                        },
                        {
                            "isAnswer": False,
                            "answerText": "Call of Duty"
                        },
                        {
                            "isAnswer": False,
                            "answerText": "Overwatch"
                        },
                        {
                            "isAnswer": False,
                            "answerText": "Fortnite"
                        }
                    ]
                },
                {
                    "questionTitle": "¿Cuál es el nombre del juego de aventuras protagonizado por Link?",
                    "questionType": "SingleChoice",
                    "answers": [
                        {
                            "isAnswer": True,
                            "answerText": "The Legend of Zelda: Breath of the Wild"
                        },
                        {
                            "isAnswer": False,
                            "answerText": "Super Mario Odyssey"
                        },
                        {
                            "isAnswer": False,
                            "answerText": "Metroid Dread"
                        },
                        {
                            "isAnswer": False,
                            "answerText": "Fire Emblem: Three Houses"
                        }
                    ]
                },
                {
                    "questionTitle": "¿Cuál es el nombre del juego de lucha que incluye personajes de múltiples franquicias de Nintendo?",
                    "questionType": "SingleChoice",
                    "answers": [
                        {
                            "isAnswer": True,
                            "answerText": "Super Smash Bros. Ultimate"
                        },
                        {
                            "isAnswer": False,
                            "answerText": "Street Fighter V"
                        },
                        {
                            "isAnswer": False,
                            "answerText": "Tekken 7"
                        },
                        {
                            "isAnswer": False,
                            "answerText": "Mortal Kombat 11"
                        }
                    ]
                },
                {
                    "questionTitle": "¿Cuál es el nombre del juego de plataformas protagonizado por Mario?",
                    "questionType": "SingleChoice",
                    "answers": [
                        {
                            "isAnswer": True,
                            "answerText": "Super Mario Odyssey"
                        },
                        {
                            "isAnswer": False,
                            "answerText": "Sonic Mania"
                        },
                        {
                            "isAnswer": False,
                            "answerText": "Crash Bandicoot N. Sane Trilogy"
                        },
                        {
                            "isAnswer": False,
                            "answerText": "Rayman Legends"
                        }
                    ]
                }
    ]
}
# Ejemplo de uso
cuestionario = {
    "name": "Generic",
    "language": "ES",
    "questions": [
        {
            "questionTitle": "¿Qué influencer es más conocido por gestionar torneos?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": False,
                    "answerText": "Loulogio"
                },
                {
                    "isAnswer": True,
                    "answerText": "Mr Beast"
                },
                {
                    "isAnswer": False,
                    "answerText": "PewDiePie"
                },
                {
                    "isAnswer": False,
                    "answerText": "ElRubiusOMG"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es el creador de contenido más seguido en YouTube?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": False,
                    "answerText": "Markiplier"
                },
                {
                    "isAnswer": True,
                    "answerText": "PewDiePie"
                },
                {
                    "isAnswer": False,
                    "answerText": "Jacksepticeye"
                },
                {
                    "isAnswer": False,
                    "answerText": "DanTDM"
                }
            ]
        },
        {
            "questionTitle": "¿Qué plataforma es conocida por sus transmisiones en vivo de videojuegos?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Twitch"
                },
                {
                    "isAnswer": False,
                    "answerText": "Instagram"
                },
                {
                    "isAnswer": False,
                    "answerText": "Twitter"
                },
                {
                    "isAnswer": False,
                    "answerText": "Facebook"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es conocido por su serie de videos 'Unboxing'?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": False,
                    "answerText": "Ninja"
                },
                {
                    "isAnswer": False,
                    "answerText": "Pokimane"
                },
                {
                    "isAnswer": True,
                    "answerText": "Marques Brownlee"
                },
                {
                    "isAnswer": False,
                    "answerText": "Linus Tech Tips"
                }
            ]
        },
        {
            "questionTitle": "¿Qué plataforma es famosa por sus videos cortos y virales?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": False,
                    "answerText": "Facebook"
                },
                {
                    "isAnswer": True,
                    "answerText": "TikTok"
                },
                {
                    "isAnswer": False,
                    "answerText": "LinkedIn"
                },
                {
                    "isAnswer": False,
                    "answerText": "Snapchat"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es conocido por su canal de cocina en YouTube?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": False,
                    "answerText": "PewDiePie"
                },
                {
                    "isAnswer": False,
                    "answerText": "Mr Beast"
                },
                {
                    "isAnswer": True,
                    "answerText": "Binging with Babish"
                },
                {
                    "isAnswer": False,
                    "answerText": "Ninja"
                }
            ]
        },
        {
            "questionTitle": "¿Qué plataforma es conocida por sus 'Stories' que desaparecen en 24 horas?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": False,
                    "answerText": "Twitter"
                },
                {
                    "isAnswer": True,
                    "answerText": "Instagram"
                },
                {
                    "isAnswer": False,
                    "answerText": "LinkedIn"
                },
                {
                    "isAnswer": False,
                    "answerText": "YouTube"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es conocido por su contenido de maquillaje en YouTube?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "James Charles"
                },
                {
                    "isAnswer": False,
                    "answerText": "PewDiePie"
                },
                {
                    "isAnswer": False,
                    "answerText": "Mr Beast"
                },
                {
                    "isAnswer": False,
                    "answerText": "Ninja"
                }
            ]
        },
        {
            "questionTitle": "¿Qué plataforma es conocida por sus 'tweets' de 280 caracteres?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Twitter"
                },
                {
                    "isAnswer": False,
                    "answerText": "Instagram"
                },
                {
                    "isAnswer": False,
                    "answerText": "Facebook"
                },
                {
                    "isAnswer": False,
                    "answerText": "LinkedIn"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es conocido por su serie de videos 'Let's Play'?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": False,
                    "answerText": "Linus Tech Tips"
                },
                {
                    "isAnswer": True,
                    "answerText": "Markiplier"
                },
                {
                    "isAnswer": False,
                    "answerText": "Marques Brownlee"
                },
                {
                    "isAnswer": False,
                    "answerText": "James Charles"
                }
            ]
        },
        {
            "questionTitle": "¿Qué plataforma es conocida por sus 'Pins' y tableros de inspiración?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": False,
                    "answerText": "Instagram"
                },
                {
                    "isAnswer": True,
                    "answerText": "Pinterest"
                },
                {
                    "isAnswer": False,
                    "answerText": "Facebook"
                },
                {
                    "isAnswer": False,
                    "answerText": "Snapchat"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es conocido por su contenido de tecnología en YouTube?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Linus Tech Tips"
                },
                {
                    "isAnswer": False,
                    "answerText": "James Charles"
                },
                {
                    "isAnswer": False,
                    "answerText": "Mr Beast"
                },
                {
                    "isAnswer": False,
                    "answerText": "Ninja"
                }
            ]
        },
        {
            "questionTitle": "¿Qué plataforma es conocida por sus videos de larga duración?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "YouTube"
                },
                {
                    "isAnswer": False,
                    "answerText": "TikTok"
                },
                {
                    "isAnswer": False,
                    "answerText": "Instagram"
                },
                {
                    "isAnswer": False,
                    "answerText": "Snapchat"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es conocido por su contenido de juegos en Twitch?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Ninja"
                },
                {
                    "isAnswer": False,
                    "answerText": "James Charles"
                },
                {
                    "isAnswer": False,
                    "answerText": "Mr Beast"
                },
                {
                    "isAnswer": False,
                    "answerText": "Linus Tech Tips"
                }
            ]
        },
        {
            "questionTitle": "¿Qué plataforma es conocida por sus 'Reels'?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": False,
                    "answerText": "Twitter"
                },
                {
                    "isAnswer": True,
                    "answerText": "Instagram"
                },
                {
                    "isAnswer": False,
                    "answerText": "LinkedIn"
                },
                {
                    "isAnswer": False,
                    "answerText": "Pinterest"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es conocido por su contenido de vlogs diarios en YouTube?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Casey Neistat"
                },
                {
                    "isAnswer": False,
                    "answerText": "PewDiePie"
                },
                {
                    "isAnswer": False,
                    "answerText": "Mr Beast"
                },
                {
                    "isAnswer": False,
                    "answerText": "Ninja"
                }
            ]
        },
        {
            "questionTitle": "¿Qué plataforma es conocida por sus 'Snaps' que desaparecen?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": False,
                    "answerText": "Twitter"
                },
                {
                    "isAnswer": True,
                    "answerText": "Snapchat"
                },
                {
                    "isAnswer": False,
                    "answerText": "LinkedIn"
                },
                {
                    "isAnswer": False,
                    "answerText": "YouTube"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es conocido por su contenido de ciencia en YouTube?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Vsauce"
                },
                {
                    "isAnswer": False,
                    "answerText": "PewDiePie"
                },
                {
                    "isAnswer": False,
                    "answerText": "Mr Beast"
                },
                {
                    "isAnswer": False,
                    "answerText": "Ninja"
                }
            ]
        },
        {
            "questionTitle": "¿Qué plataforma es conocida por sus 'Lives' de 24 horas?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "YouTube"
                },
                {
                    "isAnswer": False,
                    "answerText": "Instagram"
                },
                {
                    "isAnswer": False,
                    "answerText": "Twitter"
                },
                {
                    "isAnswer": False,
                    "answerText": "LinkedIn"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es conocido por su contenido de fitness en YouTube?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Athlean-X"
                },
                {
                    "isAnswer": False,
                    "answerText": "PewDiePie"
                },
                {
                    "isAnswer": False,
                    "answerText": "Mr Beast"
                },
                {
                    "isAnswer": False,
                    "answerText": "Ninja"
                }
            ]
        },
        {
            "questionTitle": "¿Qué plataforma es conocida por sus 'Fleets'?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Twitter"
                },
                {
                    "isAnswer": False,
                    "answerText": "Instagram"
                },
                {
                    "isAnswer": False,
                    "answerText": "Facebook"
                },
                {
                    "isAnswer": False,
                    "answerText": "LinkedIn"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es conocido por su contenido de viajes en YouTube?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "FunForLouis"
                },
                {
                    "isAnswer": False,
                    "answerText": "PewDiePie"
                },
                {
                    "isAnswer": False,
                    "answerText": "Mr Beast"
                },
                {
                    "isAnswer": False,
                    "answerText": "Ninja"
                }
            ]
        },
        {
            "questionTitle": "¿Qué plataforma es conocida por sus 'Stories' que desaparecen en 24 horas?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Instagram"
                },
                {
                    "isAnswer": False,
                    "answerText": "Twitter"
                },
                {
                    "isAnswer": False,
                    "answerText": "LinkedIn"
                },
                {
                    "isAnswer": False,
                    "answerText": "YouTube"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es conocido por su contenido de ciencia en YouTube?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Vsauce"
                },
                {
                    "isAnswer": False,
                    "answerText": "PewDiePie"
                },
                {
                    "isAnswer": False,
                    "answerText": "Mr Beast"
                },
                {
                    "isAnswer": False,
                    "answerText": "Ninja"
                }
            ]
        },
        {
            "questionTitle": "¿Qué plataforma es conocida por sus 'Snaps' que desaparecen?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Snapchat"
                },
                {
                    "isAnswer": False,
                    "answerText": "Twitter"
                },
                {
                    "isAnswer": False,
                    "answerText": "LinkedIn"
                },
                {
                    "isAnswer": False,
                    "answerText": "YouTube"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es conocido por su contenido de tecnología en YouTube?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Linus Tech Tips"
                },
                {
                    "isAnswer": False,
                    "answerText": "James Charles"
                },
                {
                    "isAnswer": False,
                    "answerText": "Mr Beast"
                },
                {
                    "isAnswer": False,
                    "answerText": "Ninja"
                }
            ]
        },
        {
            "questionTitle": "¿Qué plataforma es conocida por sus videos de larga duración?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "YouTube"
                },
                {
                    "isAnswer": False,
                    "answerText": "TikTok"
                },
                {
                    "isAnswer": False,
                    "answerText": "Instagram"
                },
                {
                    "isAnswer": False,
                    "answerText": "Snapchat"
                }
            ]
        },
        {
            "questionTitle": "¿Quién es conocido por su contenido de juegos en Twitch?",
            "questionType": "SingleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Ninja"
                },
                {
                    "isAnswer": False,
                    "answerText": "James Charles"
                },
                {
                    "isAnswer": False,
                    "answerText": "Mr Beast"
                },
                {
                    "isAnswer": False,
                    "answerText": "Linus Tech Tips"
                }
            ]
        },
        {
            "questionTitle": "Selecciona los colores de la bandera de España",
            "questionType": "MultipleChoice",
            "answers": [
                {
                    "isAnswer": True,
                    "answerText": "Rojo"
                },
                {
                    "isAnswer": True,
                    "answerText": "Amarillo"
                },
                {
                    "isAnswer": False,
                    "answerText": "Verde"
                }
            ]
        }
    ]
}
guardar_cuestionario(cuestionarioMChoice)