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
guardar_cuestionario(cuestionario)