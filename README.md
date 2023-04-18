# A project to implement a music fingerprint like Shazam

## 1. Prerequisites
Make sure you have the latest java and maven version

## 2. How to use it
After you build the project, just execute it and follow the instructions show in order to register a song in database or record an audio to be fingerprinted.

## 3. Documentation
In order to be able to fingerprint a music, the digital recording had to be converted from the time domain into frequency domain, by using a [Fourier Transform algorithm](https://mathworld.wolfram.com/FourierTransform.html). After the conversion, for every bit of song it was extracted some peak frenquencies from a range of specific and notorious frequencies. Each of these peak frequencies set were then merged into a hash and stored with its respective time frame in order to be compared later.

When an audio is recorded into the application, the same conversion, frequency extraction and hashing process is applied, for it to be compared with the hashes available in the database. If a match is found, then a time offset is calculated for it to be later compared with other potential matches. The music with the highest matches is considered as the most probable song.

## 4. References:

https://www.ee.columbia.edu/~dpwe/papers/Wang03-shazam.pdf
