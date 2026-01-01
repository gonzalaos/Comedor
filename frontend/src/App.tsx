import React from 'react';
// import { QueryClientProvider } from '@tanstack/react-query';
// import { appQueryClient } from './config/app-query-client';
// import { Route, Switch } from 'wouter';
// import './App.css';
import IngredientsPage from './pages/IngredientsPage';

const App: React.FC = () => {
  // return (
  //   <QueryClientProvider client={appQueryClient}>
  //     <div className='app'>
  //       <Switch>
  //           <Route path="/ingredients" component={IngredientsPage} />
  //       </Switch>
  //     </div>
  //   </QueryClientProvider>
  // )
  return (
    <IngredientsPage />
  )
}

export default App;
