@import hexlet.code.dto.UrlPage
@param UrlPage page

@template.layout.page(
    page = page,
    content = @`
        <div class="max-w-7xl mx-auto my-10 px-6 font-sans">
            <h1 class="text-4xl font-normal text-gray-950 mb-6 tracking-tight">
                Сайт: ${page.getUrl().getName()}
            </h1>
            <div class="overflow-x-auto m-10 rounded-md">
                <table data-test="urls" class="min-w-full bg-white border border-gray-300">
                    <tbody>
                        <tr class="bg-zing-100">
                            <td class="px-4 py-2 text-left">ID</td>
                            <td class="px-4 py-2 text-left">${page.getUrl().getId()}</td>
                        </tr>
                        <tr class="bg-zing-300">
                            <td class="px-4 py-2 text-left">Имя</td>
                            <td class="px-4 py-2 text-left">${page.getUrl().getName()}</td>
                        </tr>
                        <tr class="bg-zing-100">
                            <td class="px-4 py-2 text-left">Дата создания</td>
                            <td class="px-4 py-2 text-left">${page.getUrl().getCreatedAt()}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

    <div class="max-w-6xl mx-auto p-6 bg-white font-sans">
        <h2 class="text-3xl font-normal text-gray-800 mb-3">
            Проверки
        </h2>

        <button
                type="button"
                class="bg-blue-600 hover:bg-blue-700 text-white text-sm font-normal px-4 py-2 rounded transition-colors mb-5"
        >
            Запустить проверку
        </button>

        <div class="overflow-x-auto">
            <table class="w-full border-collapse border border-gray-200 text-left text-sm text-gray-800">
                <thead>
                <tr>
                    <th class="border border-gray-200 px-3 py-3 font-bold w-16 align-bottom">
                        ID
                    </th>
                    <th class="border border-gray-200 px-3 py-3 font-bold w-24 align-bottom leading-tight">
                        Код<br>ответа
                    </th>
                    <th class="border border-gray-200 px-3 py-3 font-bold w-32 align-bottom">
                        h1
                    </th>
                    <th class="border border-gray-200 px-3 py-3 font-bold w-40 align-bottom">
                        title
                    </th>
                    <th class="border border-gray-200 px-3 py-3 font-bold align-bottom">
                        description
                    </th>
                    <th class="border border-gray-200 px-3 py-3 font-bold w-44 align-bottom">
                        Дата проверки
                    </th>
                </tr>
                </thead>
                <tbody>
                <!-- Пример строки с данными (можете раскомментировать для проверки) -->
                <!--
                <tr>
                  <td class="border border-gray-200 px-3 py-2.5">1</td>
                  <td class="border border-gray-200 px-3 py-2.5">200</td>
                  <td class="border border-gray-200 px-3 py-2.5">Главная страница</td>
                  <td class="border border-gray-200 px-3 py-2.5">Сайт компании</td>
                  <td class="border border-gray-200 px-3 py-2.5">Описание сайта</td>
                  <td class="border border-gray-200 px-3 py-2.5">14.11.2026</td>
                </tr>
                -->
                </tbody>
            </table>
        </div>
    </div>

`
)