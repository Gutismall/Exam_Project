# Automatic Exam Generator

## Overview

Automatic Exam Generator is a Java-based application designed to streamline the process of creating and managing exams. The project is currently a work in progress, but it is built on solid foundations with a focus on maintainability, scalability, and ease of use.

## Features

- **Closed Questions**: Supports multiple-choice questions with one or more correct answers.
- **Open Questions**: Supports open-ended questions with text-based answers.
- **Exam File Creation**: Generates exam files in a structured format.
- **Solution File Creation**: Generates solution files for the exams.

## Project Structure

- `src/AutomaticExam.java`: Main class responsible for creating exam and solution files.
- `src/ClosedQuestion.java`: Class representing closed (multiple-choice) questions.
- `src/OpenQuestion.java`: Class representing open-ended questions.
- `src/Question.java`: Abstract base class for different types of questions.
- `src/Answer.java`: Class representing an answer to a question.

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/Gutismall/automatic-exam-generator.git
    ```
2. Open the project in IntelliJ IDEA or your preferred IDE.
3. Build the project to resolve dependencies.

## Usage

1. Create instances of `ClosedQuestion` and `OpenQuestion`.
2. Add questions to a `Set<Question>`.
3. Use `AutomaticExam` to generate exam and solution files.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License

This project is licensed under the MIT License.

## Acknowledgements

- Built with Java.
- Developed using IntelliJ IDEA.

## Status

**Work in Progress**: This project is actively being developed. New features and improvements are being added regularly.
