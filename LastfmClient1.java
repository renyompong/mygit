package lastfm;

import java.util.Collection;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Chart;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;

public class LastfmClient1 {

	public static void main(String[] args) {
		//String apiKey = "7f9c27c417d6a205b42139b9aeebb5bc";
		String apiKey = "89ea347e8248e5fbabbee9ffe06af1bc";

		String user = "re_nyom_pong";//ユーザIDを入力

		//int page = 1;

		int limit = 2;

		//PaginatedResult<Track> result = User.getLovedTracks(user, page, apiKey);//ユーザの好きな曲を取得
		Chart<Track> result = User.getWeeklyTrackChart(user,limit, apiKey);
		for(Track track : result.getEntries()) {
			String artist = track.getArtist();
			int limit2 = 5;
			System.out.println("★Artist: " + artist);//ユーザの好きな曲のアーティストを表示

			Collection<Artist> collection = Artist.getSimilar(artist, limit2, apiKey);//似ているアーティストを取得
			for(Artist anArtist : collection){
				System.out.println("  〇Similar artist: " + anArtist);//似ているアーティストを表示
			    String simArtist = anArtist.getName();
				Collection<Track> topTracks =Artist.getTopTracks(simArtist,apiKey);//似ているアーティストのトップ曲を取得
               int trackNumber = 1;
			   for(Track topTrack : topTracks ){
				System.out.println("       ♪TopTracks :" + topTrack.getName());//似ているアーティストのトップ曲を表示
				if(trackNumber ==3){
					break;
				}
				trackNumber++;
				}
			   }

			}
		}
	}
