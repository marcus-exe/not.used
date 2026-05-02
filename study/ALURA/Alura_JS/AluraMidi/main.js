function soundPlayer(idAudioElement) {
    const element = document.querySelector(idAudioElement);
    if (element === null) {
        console.log("Element not found")
    } else {
        element.play()
    }
}

const keyList = document.querySelectorAll('.tecla');



for (let counter = 0; counter < keyList.length; counter++) {

    const key = keyList[counter];
    const instrument = key.classList[1];
    const idAudio = `#som_${instrument}`;//template string

    key.onclick = function () { 
        soundPlayer(idAudio);
    };

    key.onkeydown = function (event) {
        if (event.code === 'Space' || event.code === 'Enter')
        key.classList.add('ativa')
    }
    key.onkeyup= function () {
        key.classList.remove('ativa')
    }
}
