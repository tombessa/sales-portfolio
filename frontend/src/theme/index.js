import { createTheme, responsiveFontSizes } from '@material-ui/core/styles';

const palette = {
    primary: { main: '#0067a0' },
    secondary: { main: '#007B5F' }
  };
const themeName = 'JT';

let theme =  createTheme({ palette, themeName });

theme = responsiveFontSizes(theme);

export default theme;