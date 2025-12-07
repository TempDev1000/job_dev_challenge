import * as path from 'path';
import * as fs from 'fs';
import * as express from 'express';
import * as nunjucks from 'nunjucks';

export class Nunjucks {
  constructor(public developmentMode: boolean) {
    this.developmentMode = developmentMode;
  }

  enableFor(app: express.Express): void {
    app.set('view engine', 'njk');

    // Define template paths
    const localViews = path.join(process.cwd(), 'src', 'main', 'views');
    const govukTemplates = path.join(process.cwd(), 'node_modules', 'govuk-frontend', 'govuk');
    const govukComponents = path.join(process.cwd(), 'node_modules', 'govuk-frontend');

    console.log('Local views exists?', fs.existsSync(localViews));
    console.log('GovUK templates exists?', fs.existsSync(govukTemplates));
    console.log('GovUK components exists?', fs.existsSync(govukComponents));

    const templatePaths = [localViews, govukTemplates, govukComponents];

    // Configure Nunjucks
    nunjucks.configure(templatePaths, {
      autoescape: true,
      watch: this.developmentMode,
      express: app,
    });

    // Add pagePath to locals
    app.use((req, res, next) => {
      res.locals.pagePath = req.path;
      next();
    });
  }
}