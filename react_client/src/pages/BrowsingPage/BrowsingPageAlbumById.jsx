import React, { useState, useEffect } from 'react';
import { Grid, Typography } from '@material-ui/core';

import GridLayout from '../../layouts/GridLayout/GridLayout';
import SongCard from '../../components/Cards/SongCard';
import api from '../../api/api';
import useStyles from './styles';

const BrowsingPageAlbumById = ({ id }) => {
  const classes = useStyles();

  const [songsData, setSongsData] = useState([]);
  const [albumName, setAlbumName] = useState('');

  useEffect(() => {
    const getSongs = async () => {
      const { data } = await api.get(`/albums/${id}`);
      //TODO error handler

      setAlbumName(data.name);

      let songsArr = [];
      for (const songId of data.songs) {
        const songData = await api.get(`/songs/${songId}`);
        songsArr.push(songData.data);
      }

      setSongsData(songsArr);
    };

    getSongs();
  }, [id]);

  return (
    <>
      <GridLayout>
        <Grid item xs={12}>
          <Typography className={classes.heading}>{albumName}</Typography>
        </Grid>
        {songsData.map((data, index) => {
          return (
            <Grid key={data.id} item xs={12} md={6} lg={4}>
              <SongCard
                data={data}
                backgroundColor={'#8D99AE'}
              />
            </Grid>
          );
        })}
      </GridLayout>
    </>
  );
};

export default BrowsingPageAlbumById;