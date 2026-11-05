@import hexlet.code.dto.UrlsPage
@param UrlsPage page

@template.layout.page(
content = @`
    <div class="max-w-7xl mx-auto my-10 px-6 font-sans">
        <h1 class="text-4xl font-normal text-gray-950 mb-6 tracking-tight">
            Сайты
        </h1>
        <div class="overflow-x-auto m-10 rounded-md">
            <table data-test="urls" class="min-w-full bg-white border border-gray-300">
                <thead class="bg-gray-200">
                <tr class="bg-zink-300">
                    <th class="px-4 py-2 text-left">ID</th>
                    <th class="px-4 py-2 text-left">Имя</th>
                    <th class="px-4 py-2 text-left">Последняя проверка</th>
                    <th class="px-4 py-2 text-left">Код ответа</th>
                </tr>
                </thead>
                <tbody>
                    <tr class="bg-zing-100">
                    @for(var url: page.getUrls())
                        <td class="px-4 py-2 text-left">${url.getId()}</td>
                        <td class="px-4 py-2 text-left"><a href="/urls/${url.getId()}">${url.getName()}</a></td>
                        <td class="px-4 py-2 text-left">${url.getCreatedAt()}</td>
                        <td class="px-4 py-2 text-left">${url.getCreatedAt()}</td>
                    </tr>
                    @endfor
                </tbody>
            </table>
        </div>
    </div>
`
)