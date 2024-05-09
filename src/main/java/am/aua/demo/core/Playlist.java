package am.aua.demo.core;

public class Playlist {
    /*
    private String name;
    private ArrayList<Song> songs;
    private SongPlayer songCore;

    public Playlist(String name, SongPlayer songCore) {
        this.name = name;
        this.songs = new ArrayList<>();
        this.songCore = songCore;
    }

    public String getName() {
        return name;
    }


    public ArrayList<Integer> getSongIds() {
        ArrayList<Integer> songIds = new ArrayList<>();
        for (Song song : songs) {
            songIds.add(song.getId());
        }
        return songIds;
    }


    public void addSong(int songId) throws FileNotFoundException, DuplicateSongException {
        for (Song song : songs) {
            if (song.getId() == songId) {
                throw new DuplicateSongException("Duplicate song found with ID: " + songId);
            }
        }
        Song songToAdd = songCore.findSongById(songId);
        if (songToAdd != null) {
            songs.add(songToAdd);
            System.out.println("Song added to playlist: " + songToAdd.getName());
        } else {
            throw new FileNotFoundException("Song with ID " + songId + " not found.");
        }
    }


    public void display() {
        System.out.println("Playlist Name: " + name);
        System.out.println("Songs in Playlist:");
        for (Song song : songs) {
            System.out.println(song.getName());
        }
    }



    public static class PlaylistManager {
        private List<Playlist> playlists;

        public PlaylistManager() {
            this.playlists = new ArrayList<>();
        }

        public void addPlaylist(Playlist playlist) {
            playlists.add(playlist);
        }

        public void removePlaylist(Playlist playlist) {
            playlists.remove(playlist);
        }

        public List<Playlist> getAllPlaylists() {
            return new ArrayList<>(playlists);
        }
    }

    */

}
