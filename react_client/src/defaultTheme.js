const defaultTheme = {
  typography: {
    fontSize: 22,
    fontFamily: 'Raleway',
  },
  palette: {
    background: {
      default: '#3b3b3b',
    },
    primary: {
      light: '#ffffff',
      headings: '#ffffff',
      buttons: '#4e8d7c',
      hover: '#707070',
      main: '#8D99AE',
      dark: '#17860b',
    },

    error: {
      main: '#b30000',
    },
  },
  layout: {
    drawerWidth: 250,
  },
  breakpoints: {
    values: {
      xs: 0,
      sm: 600,
      md: 1080,
      lg: 1500,
      xl: 1920,
    },
  },
  overrides: {
    MuiCssBaseline: {
      '@global': {
        '*::-webkit-scrollbar': {
          width: '6px',
          backgroundColor: '#8D99AE',
        },
        '*::-webkit-scrollbar-track': {
          backgroundColor: 'transparent',
        },
        '*::-webkit-scrollbar-thumb': {
          backgroundColor: '#320b86',
        },
        '*::-webkit-scrollbar-thumb:hover': {
          backgroundColor: '#25085e',
        },
      },
    },
  },
};

export default defaultTheme;
