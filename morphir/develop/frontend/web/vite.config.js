import { spawnSync } from "child_process";
import {resolve} from "path";
import { defineConfig } from "vite";

function isDev() {
    return process.env.NODE_ENV !== "production";
}

function printMillTask(task) {
    const args = ["-s", "--no-server", "--disable-ticker", `morphir.develop.frontend.${task}`];
    const processCwd = resolve(process.cwd(),"..","..","..","..")

    const options = {
        cwd:processCwd,
        stdio: [
            "pipe", // StdIn.
            "pipe", // StdOut.
            "inherit", // StdErr.
        ],
    };
    const result = process.platform === 'win32'
        ? spawnSync("./mill.bat", args.map(x => `"${x}"`), { shell: true, ...options })
        : spawnSync("./mill", args, options);

    if (result.error)
        throw result.error;
    if (result.status !== 0)
        throw new Error(`mill process failed with exit code ${result.status}`);
    return result.stdout.toString('utf8').trim().split('\n').slice(-1)[0];
}

const linkOutputDir = isDev()
    ? printMillTask("fastLinkOut")
    : printMillTask("fullLinkOut");

export default defineConfig({
    resolve: {
        alias: [
            {
                find: "@linkOutputDir",
                replacement: linkOutputDir,
            },
        ],
    }
});