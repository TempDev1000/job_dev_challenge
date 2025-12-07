import { Application, Request, Response } from 'express';
import axios from 'axios';

export default (app: Application) => {

  /**
   * GET /tasks
   * Renders the task creation form.
   * This does not fetch tasks from the backend; it only displays the form.
   */
  app.get('/tasks', (_req: Request, res: Response) => {
    res.render('task-create.njk'); // We'll make this template
  });

  /**
   * POST /tasks
   * Submits a new task to the Spring Boot backend.
   * - Converts dueDate to ISO format before sending.
   * - On success, renders a confirmation page with the created task.
   * - On failure, renders the confirmation page with an error message.
   */
  app.post('/tasks', async (req: Request, res: Response) => {
  try {
    const { title, description, status, dueDate } = req.body;

    // Convert dueDate to full ISO string
    const isoDueDate = new Date(dueDate).toISOString().split('.')[0];

    const response = await axios.post('http://localhost:4000/api/tasks', {
      title,
      description,
      status,
      dueDate: isoDueDate
    });

      const task = response.data;

     // Format dueDate in UK format for display in Nunjucks
      task.formattedDueDate = new Date(task.dueDate).toLocaleString('en-GB', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: false
      });

    // Render confirmation page
    res.render('task-confirmation.njk', { task, message: 'Task successfully created!' });
  } catch (err: any) {
    console.error('Error creating task:', err.message);
    res.status(err.response?.status || 500).render('task-confirmation.njk', {
      task: null,
      message: 'Failed to create task',
      error: err.message
    });
  }
});

};