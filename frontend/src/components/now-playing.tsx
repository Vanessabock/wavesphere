import { Station } from "../types/Station.ts";
import React, { useEffect, useRef, useState } from "react";
import { PauseIcon } from "../icons/pause-icon.tsx";
import { PlayIcon } from "../icons/play-icon.tsx";
import { VolumeIcon } from "../icons/volume-icon.tsx";
import { MuteIcon } from "../icons/mute-icon.tsx";
import Slider from "@mui/joy/Slider";

type NowPlayingProps = {
  nowPlayingStation: Station;
  stationOnNowPlayingScreen: Station;
  audioSource: string;
  togglePlayPause: (station: Station) => void;
};

export const NowPlaying: React.FC<NowPlayingProps> = ({
  nowPlayingStation,
  stationOnNowPlayingScreen,
  audioSource,
  togglePlayPause
}) => {
  const audioPlayer = useRef<HTMLAudioElement>(null);
  const [volume, setVolume] = useState<number>(0.5);

  useEffect(() => {
    if (audioPlayer.current) {
      audioPlayer.current.volume = volume;
    }
  }, [volume]);

  const toggleRadio = () => {
    if (nowPlayingStation.stationuuid) {
      togglePlayPause(nowPlayingStation);
    } else {
      togglePlayPause(stationOnNowPlayingScreen);
    }
  };

  const toggleSlider = (_event: Event, newValue: number | number[]) => {
    setVolume(newValue as number);
  };

  return (
    <>
      {stationOnNowPlayingScreen.stationuuid && (
        <div className="sticky bottom-0 flex h-40 w-full flex-col bg-[#17233c] text-2xl text-[#f8f1e6]">
          <div className="m-4 flex justify-center">
            {stationOnNowPlayingScreen.name}
          </div>
          <div className="ml-7 flex w-1/2 flex-row justify-between pl-5">
            <div className="flex w-1/2 flex-row items-center gap-4 px-5">
              {volume === 0 ? <MuteIcon /> : <VolumeIcon />}
              <Slider
                min={0}
                max={1}
                step={0.01}
                defaultValue={0.5}
                onChange={toggleSlider}
              />
            </div>
            <button
              className="flex justify-center border-transparent"
              onClick={toggleRadio}
            >
              {nowPlayingStation.stationuuid ? (
                <PauseIcon size={"50px"} />
              ) : (
                <PlayIcon size={"50px"} />
              )}
            </button>
          </div>
          <audio
            className="hidden"
            ref={audioPlayer}
            controls
            autoPlay
            src={audioSource}
          ></audio>
        </div>
      )}
    </>
  );
};
