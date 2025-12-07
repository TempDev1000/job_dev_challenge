import * as path from 'path';

import { HTTPError } from './HttpError';
import { Nunjucks } from './modules/nunjucks';

import * as bodyParser from 'body-parser';
import cookieParser from 'cookie-parser';
import express from 'express';
import { glob } from 'glob';
//import favicon from 'serve-favicon';
import fs from 'fs';

const { setupDev } = require('./development');

const env = process.env.NODE_ENV || 'development';
const developmentMode = env === 'development';

export const app = express();
app.locals.ENV = env;

new Nunjucks(developmentMode).enableFor(app);

//app.use(favicon(path.join(__dirname, '/public/assets/images/favicon.ico')));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use((req, res, next) => {
  res.setHeader('Cache-Control', 'no-cache, max-age=0, must-revalidate, no-store');
  next();
});

const routePattern = path.join(
  __dirname,
  "routes",
  "**",
  "*.{ts,js}"
);

console.log("Glob pattern:", routePattern);

const routeFiles = glob.sync(routePattern.replace(/\\/g, "/"), { nodir: true });
console.log("Found route files:", routeFiles);

console.log("DEBUG __dirname:", __dirname);
console.log("DEBUG cwd:", process.cwd());
console.log("DEBUG expected routes dir:", path.join(__dirname, "routes"));
console.log(
  "DEBUG routes dir exists:",
  fs.existsSync(path.join(__dirname, "routes"))
);

routeFiles.forEach(file => {
  const route = require(file);
  if (route && route.default) {
    route.default(app);
  }
});

routeFiles.forEach((file) => {
  const route = require(file);
  if (route && route.default) {
    route.default(app);
  }
});


setupDev(app, developmentMode);

// Catch-all 404 route
app.use((req: express.Request, res: express.Response, next: express.NextFunction) => {
  res.status(404).render('not-found');
});


// error handler
app.use((err: HTTPError, req: express.Request, res: express.Response, next: express.NextFunction) => {
  console.log(err);

  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = env === 'development' ? err : {};
  res.status(err.status || 500);
  res.render('error');
});

