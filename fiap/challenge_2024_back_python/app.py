from flask import Flask, request, jsonify
from google_module import gemini_api

app = Flask(__name__)


# Home Page
@app.route('/')
def home():
    return "Index Page"

# Request Page
@app.route('/submit', methods=['POST'])
def submit():
    if request.is_json:
        data = request.get_json()
        json_message = data.get('message')
        google_response = gemini_api.chat_session.send_message(json_message)
        return f"google response: {google_response.text}"
    else:
        return jsonify({"error": "Request must be JSON"}), 400 

if __name__ == '__main__':
    app.run(debug=True)
