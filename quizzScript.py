import firebase_admin
from firebase_admin import credentials, firestore
cred = credentials.Certificate('./firebase-admin-services.json')
firebase_admin.initialize_app(cred)
db = firestore.client()

def guardar_cuestionario(cuestionario):
    collection_name = 'Quizzes'
    doc_ref = db.collection(collection_name).document(cuestionario['nombre'])
    doc_ref.set(cuestionario)
    print(f"Cuestionario {cuestionario['nombre']} guardado exitosamente")

# Ejemplo de uso
cuestionario = {
    "nombre": "Generic",
    "idioma": "ES",
    "preguntas": [
        {
            "questionTitle": "¿Cuál es la capital de España?",
            "questionType": "SingleChoice",
            "answers": [
                {"isAnswer": False, "answerText": "Barcelona"},
                {"isAnswer": True, "answerText": "Madrid"},
                {"isAnswer": False, "answerText": "Valencia"}
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