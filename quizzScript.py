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
            "questionTitle":  "¿Que influencer es más conocido por gestionar torneos?",
            "questionType": "SingleChoice",
            "answers": [
                {"isAnswer": False, "answerText": "Loulogio"},
                {"isAnswer": True, "answerText": "Mr Beast"},
                {"isAnswer": False, "answerText": "PewDiePie"},
                {"isAnswer": False, "answerText": "ElRubiusOMG"}
            ]
        },
        {
            "questionTitle": "Selecciona los colores de la bandera de España",
            "questionType": "MultipleChoice",
            "answers": [
                {"isAnswer": True, "answerText": "Rojo"},
                {"isAnswer": True, "answerText": "Amarillo"},
                {"isAnswer": False, "answerText": "Verde"}
            ]
        }
    ]
}

guardar_cuestionario(cuestionario)