import { Application, Request, Response } from 'express';
import axios from 'axios';


/**
 * Frontend route setup for listing tasks.
 * 
 * Exposes the GET /list-tasks route:
 * - Sends a GET request to the backend /api/tasks endpoint.
 * - Receives the list of tasks and renders the 'tasks-list.njk' template.
 * - If an error occurs, logs it and sends an HTTP error response.
 */
export default (app: Application) => {

  app.get('/list-tasks', async (_req: Request, res: Response) => {
    try {
      const response = await axios.get('http://localhost:4000/api/tasks');
      const tasks = response.data;

      res.render('tasks-list.njk', { tasks });
    } catch (err: any) {
      console.error('Error fetching tasks:', err.message);
      res.status(err.response?.status || 500).send('Failed to fetch tasks');
      //could change to render error page
    }
  });

} 
