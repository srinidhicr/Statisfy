import sys
import spotipy
from spotipy.oauth2 import SpotifyClientCredentials

def search_spotify_tracks(search_text):
    # Replace these with your Spotify API credentials
    client_id = '7c50ced03f3e49e69390703ba3caacfd'
    client_secret = '4250c8689ec04538bcf23b52bf5aa868'

    # Initialize Spotipy with client credentials flow
    client_credentials_manager = SpotifyClientCredentials(client_id=client_id, client_secret=client_secret)
    sp = spotipy.Spotify(client_credentials_manager=client_credentials_manager)

    # Search for tracks
    results = sp.search(search_text, type='track', limit=10)

    # Extract and print track names
    tracks = results['tracks']['items']
    for track in tracks:
        print(f"Track Name: {track['name']}")
        print(f"Artist: {', '.join(artist['name'] for artist in track['artists'])}")
        print(f"Album: {track['album']['name']}")
        print(f"Spotify URL: {track['external_urls']['spotify']}")
        print("\n")

if len(sys.argv) > 1:
    search_text = sys.argv[1]
    search_spotify_tracks(search_text)
else:
    print("No search text provided")
