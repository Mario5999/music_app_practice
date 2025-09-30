const player = {
    _past_songs: [],
    _nextSongs: [],
    _actualSong: null,
    _controller: media,
    _progress_bar: document.getElementById("progress"),
    initializePlayer(){
        this._actualSong
        this._progress_bar.max = 100;
        this_progress_bar.value = 0;
        this.initializeControlMedia(false);
        this._controller.addEventListener('mediaupdate', function (){
            const progress_value = (this.currentTime / this.duration) *100;
            player._progress_bar.value = progress_value;
        });
        this._progress_bar.addEventListener('input', function(){
            player._controller.currentTime = (this.value / 100) * player._controller.duration;
        })
    },
    initializeControlMedia: function (play) {
        this._progress_bar.value = 0;
        if(play){
            this._controller.play();
        }
    },
    getnextsong: function(){
        return this._nextSongs.pop();
    }
}