import { Application } from 'express';
import axios from 'axios';


/**
 * Frontend route setup for the home page.
 * 
 * Exposes the GET /home route:
 * - Connects to the backend to fetch example case data.
 * - Logs the backend response to the console for debugging.
 * - Renders the 'home' template with example data if successful.
 * - Renders the 'home' template empty if the request fails.
 */
export default function (app: Application): void {
  app.get('/home', async (req, res) => {
    try {
      // An example of connecting to the backend (a starting point)
      const response = await axios.get('http://localhost:4000/get-example-case');
      console.log(response.data);
      res.render('home', { "example": response.data });
    } catch (error) {
      console.error('Error making request:', error);
      res.render('home', {});
    }
  });
}
