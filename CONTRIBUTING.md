# Contributing Guidelines

Thank you for considering contributing to the **AutoCare REST API** ! By participating, you're helping to build a more reliable and feature-rich community project. Please take a moment to review these guidelines to ensure a smooth collaboration.

## Table of Content

* [Getting Started](#getting-started)
* [Reporting Issues](#reporting-issues)
* [Suggesting Features](#suggesting-features)
* [Submitting Pull Requests](#submitting-pull-requests)
* [Code Quality and Maintenance](#code-quality-and-maintenance)
    + [Code Formatting](#code-formatting)
    + [Git Hooks](#git-hooks)
    + [Branch Naming Convention](#branch-naming-convention)
* [Testing](#testing)
* [Documentation](#documentation)
* [Licensing](#licensing)
    
## Getting Started

1. [Fork this repository](https://docs.github.com/en/get-started/exploring-projects-on-github/contributing-to-a-project#about-forking), then clone your fork to your local machine
2. Set up the project by following the instructions in the [README](./README.md) file (check **Technology Stack** and **Getting Started** sections)
3. Ensure that the project builds successfully by running `./mvnw clean install`.

## Reporting Issues

If you encounter a bug, please open an [issue](https://docs.github.com/en/issues/tracking-your-work-with-issues/about-issues#about-issues) and provide enough details.

- Describe the problem and your environment (OS, Browser, IDE etc.).
- Provide the steps to reproduce the issue.
- Show any relevant logs or stack traces you have.
- Add notes, screenshots or attachments if needed.

## Suggesting Features

Feature suggestions are welcome! Before you start working on a new feature :
- Open an issue to discuss your idea.
- Provide a clear and concise description of the feature.
- Outline the benefits and potential drawbacks.

## Submitting Pull Requests

When you're ready to submit your changes:

1. Ensure your code adheres to the project's [Code Style](#code-quality-and-maintenance).
2. Write or update tests to cover your changes.
3. Document your changes where necessary.
4. Push your changes to a branch in your forked repository.
5. Open a pull request (PR) to the main repository's `main` branch.

When submitting a PR, please:

1. Link to the relevant issue (if possible). 
2. Provide a brief description of what your PR does.
3. Ensure all tests pass by running `mvn verify`.
4. Keep your PR small and focused: avoid combining unrelated changes.

## Code Quality and Maintenance

To maintain a consistent code style and ensure high code quality, we have integrated several tools and practices into our project:

### Code Formatting

1. **Spotless**: Ensures that the code adheres to defined formatting rules. Spotless helps keep the codebase clean and uniformly formatted.

2. **Checkstyle**: Assists in writing code that conforms to Java standard coding practices. It enforces a set of coding standards and helps maintain good quality.

_Note: If you're new to these tools, there’s no need to worry. They are already configured and will work seamlessly in the background._

### Git Hooks

To ensure code quality and proper branching, we use Git hooks managed by the [**Git Build Hook Maven Plugin**](https://github.com/rudikershaw/git-build-hook). This plugin facilitates the management of hook scripts (check `./configs/git-hooks/pre-commit` file).

This technique helps prevent poorly formatted or non-compliant code from being committed or pushed.

Currently, there are 2 sorts of hooks :

1. **Pre-commit**: Automatically format code and run checks before allowing a commit. If issues are found, the commit will be aborted until the issues are resolved.

2. **Pre-push**: (check the [next section](#branch-naming-convention))

### Branch Naming Convention

To maintain clarity and consistency, we follow a structured branch naming convention. A pre-push Git hook is in place to enforce this convention (check `./configs/git-hooks/pre-push` file).

## Testing

Serious bug fixes or improvements must be accompanied by tests to ensure that new features or fixes do not break existing functionality. 

Ensure that your tests pass by running `mvn test`.

## Documentation

Clear documentation is crucial for the project’s usability:

- Update the [README](README.md) file if your changes affect the setup or usage of the project.
- Add Javadoc comments to all public classes and methods.
- If your contribution requires significant details, create or update relevant markdown file(s) in the [/docs](./docs) directory.

## Licensing

By contributing to this project, you agree that your contributions will be licensed under the [GNU Affero General Public License (AGPL)](https://www.gnu.org/licenses/agpl-3.0.en.html). Check the [LICENSE](./LICENSE) file for more details.
