import * as elements from "./application/htm_elementes.js";
import player from "./player.js";
import songs from "../ports/multimedia.js";

export default function (){
    window.addEventListener('DOMContentLoaded',() => {
        player.initializePlayer();
        const playingNow = songs[Player._actualSong];
    });

    elements.lastest.addEventListener('click', function(){
        if(!last.length == 0){
            playlist.push(playingNow);
            playingNow = last.pop();
            loadSong(playingNow)
        }
    });

    elements.forward.addEventListener('click', function(){
        if(playlist.length == 0){
            last.pus
        }
    });
    elements.play_btn.addEventListener("click",playPause);
}
