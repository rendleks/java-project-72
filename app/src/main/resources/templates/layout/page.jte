@import hexlet.code.dto.BasePage
@import gg.jte.Content
@param Content content
@param BasePage page = null

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Hexlet Javalin Example</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>

    <header class="w-full bg-[#1e222b] text-gray-300 font-sans px-6 py-4 flex items-center border-b border-gray-800">
        <div class="max-w-7xl w-full mx-auto flex items-center gap-8">
            <div class="text-white font-medium text-base tracking-wide whitespace-nowrap cursor-pointer">
                Анализатор страниц
            </div>
            <nav class="flex items-center gap-6 text-sm text-gray-400">
                <a href="/" class="hover:text-white transition-colors duration-200">
                    Главная
                </a>
                <a href="/urls" class="hover:text-white transition-colors duration-200">
                    Сайты
                </a>
            </nav>
        </div>
    </header>
    @if(page != null && page.getFlash() != null)
        <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative" role="alert">
            <span class="block sm:inline">${page.getFlash()}</span>
            <span class="absolute top-0 bottom-0 right-0 px-4 py-3">
                <svg class="fill-current h-6 w-6 text-green-500" role="button" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><title>Close</title><path d="M14.348 14.849a1.2 1.2 0 0 1-1.697 0L10 11.819l-2.651 3.029a1.2 1.2 0 1 1-1.697-1.697l2.758-3.15-2.759-3.152a1.2 1.2 0 1 1 1.697-1.697L10 8.183l2.651-3.031a1.2 1.2 0 1 1 1.697 1.697l-2.758 3.152 2.758 3.15a1.2 1.2 0 0 1 0 1.698z"/></svg>
            </span>
        </div>
    @endif
    <main>
    ${content}
    </main>
</body>
</html>