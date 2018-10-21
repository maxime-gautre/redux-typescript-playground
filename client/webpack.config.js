const path = require("path");
const autoprefixer = require('autoprefixer');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

const development = process.env.NODE_ENV === "development";

var config = {
    entry: "./src/main.tsx",
    output: {
        filename: "main.js",
        path: __dirname + "../../webapp/public"
    },
    devtool: "source-map",
    resolve: {
        extensions: [".ts", ".tsx", ".js", ".json"],
        modules: [path.resolve(__dirname, './src'), 'node_modules']
    },
    mode: development ? 'development' : 'production',
    module: {
        rules: [
            {test: /\.tsx?$/, loader: "awesome-typescript-loader"},
            {test: /\.js$/, enforce: "pre", loader: "source-map-loader"},
            {
                test: /\.css$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    {loader: 'css-loader', query: {modules: true, importLoaders: 1, localIdentName: '[local]-[hash:base64:5]'}},
                    {
                        loader: 'postcss-loader',
                        options: {plugins: (loader) => [autoprefixer({browsers: ['last 3 versions']})]}
                    }
                ]
            }
        ]
    },
    plugins: [new MiniCssExtractPlugin({filename: 'style.css'})]
};

module.exports = config;
