# Python Flask API for 2024 Challenge

This API aims to integrate with Gemini as a fast implementation solution for an existing back-end in Java

## Configure the virtual enviroment
### Define the env
Before running it, turn on the virtual env:
```
python -m venv venv
```
### Activate env
Then you can run using:
_on windows:_
```
venv\Scripts\activate
``` 
_on macOS/linux_
```
source venv/bin/activate
```
### Setup Libs
Install `requirements.txt` dependencies:
```
pip install -r requirements.txt
```
## Run the project
### Set up Keys
Create a config.py in the root of this project and paste:
```
API_KEY = 'your_api_key_here'
```
_remember to use your Google Gemini API Key_
_link for generating the key: https://aistudio.google.com/app/prompts/new_chat?pli=1_

### Run the project
```
python app.py
```

### Stoping the project and deactivating env
For stoping the project, just type `ctrl + c` on terminal

For deactivating env:
```
deactivate
```