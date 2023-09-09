const path = require('path');
const Generator = require('@asyncapi/generator');
const {readFile} = require('fs').promises;

const MAIN_TEST_RESULT_PATH = path.join('tests', 'temp', 'integrationTestResult');

const generateFolderName = () => {
    // you always want to generate to new directory to make sure test runs in clear environment
    return path.resolve(MAIN_TEST_RESULT_PATH, Date.now().toString());
};

describe('template integration tests for generated files using the generator and jms example', () => {
    jest.setTimeout(30000);

    it('should generate proper config, services and DTOs files for provided jms', async() => {
        const outputDir = generateFolderName();
        const jmsExamplePath = './jms.yaml'
        const params = {};

        const generator = new Generator(
            path.normalize('./'),
            outputDir,
            {forceWrite: true, templateParams: params}
        );
        await generator.generateFromFile(path.resolve('tests', jmsExamplePath));
    });

});