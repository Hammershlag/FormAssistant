import { render, screen } from '@testing-library/react';
import App from './App';

test('renders FormAssistant component', () => {
  render(<App />);
  const formAssistantElement = screen.getByTestId('form-assistant');
  expect(formAssistantElement).toBeInTheDocument();
});
