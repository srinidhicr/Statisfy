import spotipy
from spotipy.oauth2 import SpotifyOAuth
import json

# Set up the authentication credentials
sp = spotipy.Spotify(auth_manager = SpotifyOAuth(client_id = "7c50ced03f3e49e69390703ba3caacfd",
                                                 client_secret = "4250c8689ec04538bcf23b52bf5aa868",
                                                 redirect_uri = "http://localhost:8080/callback",
                                                 scope = "user-library-read user-read-recently-played"))

# Get the user's recently played tracks
recently_played = []
results = sp.current_user_recently_played()
recently_played.extend(results['items'])
while results['next']:
    results = sp.next(results)
    recently_played.extend(results['items'])

# Save the results to a JSON file
with open('recently_played.json', 'w', encoding='utf-8') as json_file:
    json.dump(recently_played, json_file, ensure_ascii=False, indent=4)



"""
# Get the user's saved tracks
saved_tracks = []
results = sp.current_user_saved_tracks()
saved_tracks.extend(results['items'])
while results['next']:
    results = sp.next(results)
    saved_tracks.extend(results['items'])

# Get the user's playlists
playlists = []
results = sp.current_user_playlists()
playlists.extend(results['items'])
while results['next']:
    results = sp.next(results)
    playlists.extend(results['items'])


# Print the results for user's saved tracks, playlists & recently played tracks.
print("User's saved tracks: \n")
for track in saved_tracks:
    print(f"{track['track']['name']} by {track['track']['artists'][0]['name']}")

print("\nUser's playlists: \n")
for playlist in playlists:
    print(playlist['name'])
"""
print("\nUser's recently played tracks: \n")
for track in recently_played:
    print(f"{track['track']['name']} by {track['track']['artists'][0]['name']}")

#-by @mohitmahajan095 (github)

