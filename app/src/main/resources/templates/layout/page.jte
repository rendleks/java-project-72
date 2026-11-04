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
    @if(page != null && page.getFlash() != null)
        <p>${page.getFlash()}</p>
    @endif
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
    <main>
    ${content}
    </main>
</body>
</html>